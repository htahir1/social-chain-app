from flask import Flask
from flask import jsonify
from flask_restful import Resource, Api
from flask_sqlalchemy import SQLAlchemy
from flask import request
from sqlalchemy import exc
from sqlalchemy.inspection import inspect
from sqlalchemy.ext.declarative import DeclarativeMeta
import json
####################### GLOBAL VARIABLES ############################
# some bits of text for the page.
header_text = '''
    <html>\n<head> <title>EB Flask Test</title> </head>\n<body>'''
instructions = '''
    <p><em>Hint</em>: This is a RESTful web service! Append a username
    to the URL (for example: <code>/Thelonious</code>) to say hello to
    someone specific. Nomads FTW!</p>\n'''
home_link = '<p><a href="/">Back</a></p>\n'
footer_text = '</body>\n</html>'
BASE_URL = '/api/v1/'


####################### INITIALIZATIONS############################
# EB looks for an 'application' callable by default.
application = Flask(__name__)
application.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://nomads:nomads-partytimeline@nomads-partytimeline.cstmqefvhsvd.us-west-2.rds.amazonaws.com:3306/nomads_partytimeline'
application.config['MYSQL_DATABASE_USER'] = 'nomads'
application.config['MYSQL_DATABASE_PASSWORD'] = 'nomads-partytimeline'
application.config['MYSQL_DATABASE_DB'] = 'nomads_partytimeline'
application.config['MYSQL_DATABASE_HOST'] = 'nomads-partytimeline.cstmqefvhsvd.us-west-2.rds.amazonaws.com'
application.config['MYSQL_DATABASE_PORT'] = 3306
application.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(application)
api = Api(application)
# engine = SQLAlchemy.create_engine('mysql://{0}:{1}@{2}:{3}/{4}'.format(application.config['MYSQL_DATABASE_USER'], application.config['MYSQL_DATABASE_PASSWORD'], application.config['MYSQL_DATABASE_DB'], application.config['MYSQL_DATABASE_PORT'], application.config['MYSQL_DATABASE_DB']))
# metadata = SQLAlchemy.MetaData()

####################### MODELS ############################
event_eventmember = db.Table('event_eventmember',
    db.Column('event_member_id', db.Integer, db.ForeignKey('event_members._id')),
    db.Column('event_id', db.Integer, db.ForeignKey('events._id'))
)

def new_alchemy_encoder(revisit_self = False, fields_to_expand = []):
    _visited_objs = []
    class AlchemyEncoder(json.JSONEncoder):
        def default(self, obj):
            if isinstance(obj.__class__, DeclarativeMeta):
                # don't re-visit self
                if revisit_self:
                    if obj in _visited_objs:
                        return None
                    _visited_objs.append(obj)

                # go through each field in this SQLalchemy class
                fields = {}
                for field in [x for x in dir(obj) if not x.startswith('_') and x != 'metadata']:
                    val = obj.__getattribute__(field)

                    # is this field another SQLalchemy object, or a list of SQLalchemy objects?
                    if isinstance(val.__class__, DeclarativeMeta) or (isinstance(val, list) and len(val) > 0 and isinstance(val[0].__class__, DeclarativeMeta)):
                        # unless we're expanding this field, stop here
                        if field not in fields_to_expand:
                            # not expanding this field: set it to None and continue
                            fields[field] = None
                            continue

                    fields[field] = val
                # a json-encodable dict
                return fields

            return json.JSONEncoder.default(self, obj)
    return AlchemyEncoder

class Serializer(object):

    def serialize(self):
        return {c: getattr(self, c) for c in inspect(self).attrs.keys()}

    @staticmethod
    def serialize_list(l):
        return [m.serialize() for m in l]

    def as_dict(self):
        return {c.name: getattr(self, c.name) for c in self.__table__.columns}

# Class to add, update and delete data via SQLALchemy sessions
class CRUD():
    def add(self, resource):
        db.session.add(resource)
        return db.session.commit()

    def update(self):
        return db.session.commit()

    def delete(self, resource):
        db.session.delete(resource)
        return db.session.commit()


class EventMembers(db.Model, CRUD, Serializer):
    __tablename__ = "event_members"

    _id = db.Column(db.Integer, primary_key=True)
    email_address = db.Column(db.String(254), unique=True, nullable=False)
    first_name = db.Column(db.Text, nullable=False)
    last_name = db.Column(db.Text, nullable=False)
    facebook_token = db.Column(db.String(256), unique=True)

    images = db.relationship('EventImages', backref='event_member', lazy='dynamic')

    def __init__(self, email_address, first_name, last_name, facebook_token):
        self.email_address = email_address
        self.first_name = first_name
        self.last_name = last_name
        self.facebook_token = facebook_token


    def __repr__(self):
        return '<First Name: %r>' % self.first_name
# class EventMembersSchema(Schema):
#     not_blank = validate.Length(min=1, error='Field cannot be blank')
#     _id = fields.Integer(dump_only=True)
#     email_address = fields.Email(validate=not_blank)
#     first_name = fields.String(validate=not_blank)
#     last_name = fields.String(validate=not_blank)
#     facebook_token = fields.String()
#
#     # self links
#     def get_top_level_links(self, data, many):
#         if many:
#             self_link = "/event_members/"
#         else:
#             self_link = "/event_members/{}".format(data['_id'])
#         return {'self': self_link}
#
#     class Meta:
#         type_ = 'event_members'


class EventImages(db.Model, CRUD, Serializer):
    __tablename__ = "event_images"

    _id = db.Column(db.Integer, primary_key=True)
    uri = db.Column(db.String(256), unique=True, nullable=False)
    caption = db.Column(db.String(100))
    date_taken = db.Column(db.DateTime, unique=True, nullable=False)
    date_modified = db.Column(db.DateTime, unique=True, nullable=False)

    event_id = db.Column(db.Integer, db.ForeignKey('events._id'))
    event_member_id = db.Column(db.Integer, db.ForeignKey('event_members._id'))

    def __init__(self, uri, caption, date_taken, date_modified):
        self.uri = uri
        self.caption = caption
        self.date_taken = date_taken
        self.date_modified = date_modified

    def __repr__(self):
        return '<Image URI %r>' % self.uri


class Events(db.Model, CRUD, Serializer):
    __tablename__ = "events"

    _id = db.Column(db.Integer, primary_key=True)
    event_name = db.Column(db.String(256), unique=True, nullable=False)
    event_description = db.Column(db.Text)
    date_taken = db.Column(db.DateTime, unique=True, nullable=False)
    date_modified = db.Column(db.DateTime, unique=True, nullable=False)

    images = db.relationship('EventImages', backref='event', lazy='dynamic')
    event_members = db.relationship('EventMembers', secondary=event_eventmember, backref=db.backref('events', lazy='dynamic'))

    def __init__(self, event_name, event_description, date_taken, date_modified):
        self.event_name = event_name
        self.event_description = event_description
        self.date_taken = date_taken
        self.date_modified = date_modified

    def __repr__(self):
        return '<Event %r>' % self.event_name


db.create_all()

####################### VIEW MODELS ###########################
class EventsList(Resource):
    # Function for a GET request
    def get(self):
        # Query the database and return all users
        events = Events.query.all()
        # Serialize the query results in the JSON API format
        return json.dumps()

    def post(self):
        raw_dict = request.get_json(force=True)
        event_dict = raw_dict['data']['attributes']
        try:
            pass
            # schema.validate(event_dict)
            # # Create a User object with the API data recieved
            # user = Event(event_dict['email'], event_dict['name'], event_dict['is_active'], event_dict['role'])
            # # Commit data
            # user.add(user)
            # query = Users.query.get(user.id)
            # results = schema.dump(query).data
            # return results, 201

        except exc.SQLAlchemyError as e:
            db.session.rollback()
            resp = jsonify({"error": str(e)})
            resp.status_code = 403
            return resp


class EventsUpdate(Resource):
    # http://jsonapi.org/format/#fetching
    def get(self, id):
        result = Events.query.get_or_404(id)
        return result

    # # http://jsonapi.org/format/#crud-updating
    # def patch(self, id):
    #     user = Events.query.get_or_404(id)
    #     raw_dict = request.get_json(force=True)
    #     user_dict = raw_dict['data']['attributes']
    #     try:
    #         for key, value in user_dict.items():
    #             schema.validate({key: value})
    #             setattr(user, key, value)
    #
    #         user.update()
    #         return self.get(id)
    #
    #     except exc.SQLAlchemyError as e:
    #         db.session.rollback()
    #         resp = jsonify({"error": str(e)})
    #         resp.status_code = 401
    #         return resp

            # http://jsonapi.org/format/#crud-deleting


    # def delete(self, id):
    #     event = Events.query.get_or_404(id)
    #     try:
    #         delete = event.delete(user)
    #         response = make_response()
    #         response.status_code = 204
    #         return response
    #
    #     except exc.SQLAlchemyError as e:
    #         db.session.rollback()
    #         resp = jsonify({"error": str(e)})
    #         resp.status_code = 401
    #         return resp


class EventImageList(Resource):
    # Function for a GET request
    def get(self):
        # Query the database and return all users
        events = EventImages.query.all()
        # Serialize the query results in the JSON API format
        return events
        # Function for a POST request

    def post(self):
        raw_dict = request.get_json(force=True)
        event_dict = raw_dict['data']['attributes']
        try:
            pass
            # schema.validate(event_dict)
            # # Create a User object with the API data recieved
            # user = Event(event_dict['email'], event_dict['name'], event_dict['is_active'], event_dict['role'])
            # # Commit data
            # user.add(user)
            # query = Users.query.get(user.id)
            # results = schema.dump(query).data
            # return results, 201

        except exc.SQLAlchemyError as e:
            db.session.rollback()
            resp = jsonify({"error": str(e)})
            resp.status_code = 403
            return resp


class EventImageUpdate(Resource):
    # http://jsonapi.org/format/#fetching
    def get(self, id):
        result = EventImages.query.get_or_404(id)
        return result


class EventMembersList(Resource):
    # Function for a GET request
    def get(self):
        # Query the database and return all users
        events = EventMembers.query.all()
        # Serialize the query results in the JSON API format
        return events
        # Function for a POST request

    def post(self):
        raw_dict = request.get_json(force=True)
        event_dict = raw_dict['data']['attributes']
        try:
            pass
            # schema.validate(event_dict)
            # # Create a User object with the API data recieved
            # user = Event(event_dict['email'], event_dict['name'], event_dict['is_active'], event_dict['role'])
            # # Commit data
            # user.add(user)
            # query = Users.query.get(user.id)
            # results = schema.dump(query).data
            # return results, 201

        except exc.SQLAlchemyError as e:
            db.session.rollback()
            resp = jsonify({"error": str(e)})
            resp.status_code = 403
            return resp


class EventMembersUpdate(Resource):
    # http://jsonapi.org/format/#fetching
    def get(self, id):
        result = EventMembers.query.get_or_404(id)
        return result





####################### VIEW FUNCTIONS ###########################
def say_hello(username = "World"):
    return '<p>Hello %s!</p>\n' % username

def db_test():
    # conn = mysql.connect()
    # cursor = conn.cursor()
    #
    # cursor.execute("SELECT * from test")
    # data = cursor.fetchall()
    #
    # if len(data) is 0:
    #     conn.commit()
    #     return {'StatusCode': '200', 'Message': 'User creation success'}
    # else:
    #     return {'StatusCode': '1000', 'Message': str(data[0])}

    return 'Some response'

######################### MAIN ###################################

# add a rule for the index page.
application.add_url_rule('/', 'index', (lambda: header_text +
    say_hello() + instructions + footer_text))

application.add_url_rule('/db_test', '', (lambda: db_test()))

# add a rule when the page is accessed with a name appended to the site
# URL.
application.add_url_rule('/<username>', 'hello', (lambda username:
    header_text + say_hello(username) + home_link + footer_text))

api.add_resource(EventsList, '/events.json')
api.add_resource(EventsUpdate, '/events/<id>.json')
api.add_resource(EventImageList, '/event_images.json')
api.add_resource(EventImageUpdate, '/event_images/<id>.json')
api.add_resource(EventMembersList, '/event_members.json')
api.add_resource(EventMembersUpdate, '/event_members/<id>.json')


# run the app.
if __name__ == "__main__":
    # Setting debug to True enables debug output. This line should be
    # removed before deploying a production app.

    application.debug = True
    application.run()





