from flask import Flask
from flaskext.mysql import MySQL

# print a nice greeting.
def say_hello(username = "World"):
    return '<p>Hello %s!</p>\n' % username

def db_test():
    conn = mysql.connect()
    cursor = conn.cursor()

    cursor.execute("SELECT * from test")
    rv = cursor.fetchall()
    return str(rv)

# some bits of text for the page.
header_text = '''
    <html>\n<head> <title>EB Flask Test</title> </head>\n<body>'''
instructions = '''
    <p><em>Hint</em>: This is a RESTful web service! Append a username
    to the URL (for example: <code>/Thelonious</code>) to say hello to
    someone specific. Nomads FTW!</p>\n'''
home_link = '<p><a href="/">Back</a></p>\n'
footer_text = '</body>\n</html>'

# EB looks for an 'application' callable by default.
application = Flask(__name__)
# application.config['SQLALCHEMY_DATABASE_URI'] = 'mysql://nomads:nomads-partytimeline@http://nomads-partytimeline.cstmqefvhsvd.us-west-2.rds.amazonaws.com:3306/nomads_partytimeline'
mysql = MySQL()
application.config['MYSQL_DATABASE_USER'] = 'nomads'
application.config['MYSQL_DATABASE_PASSWORD'] = 'nomads-partytimeline'
application.config['MYSQL_DATABASE_DB'] = 'nomads_partytimeline'
application.config['MYSQL_DATABASE_HOST'] = 'nomads-partytimeline.cstmqefvhsvd.us-west-2.rds.amazonaws.com'
application.config['MYSQL_DATABASE_PORT'] = 3306

mysql.init_app(application)
# add a rule for the index page.
application.add_url_rule('/', 'index', (lambda: header_text +
    say_hello() + instructions + footer_text))

application.add_url_rule('/db_test', '', (lambda: db_test()))

# add a rule when the page is accessed with a name appended to the site
# URL.
application.add_url_rule('/<username>', 'hello', (lambda username:
    header_text + say_hello(username) + home_link + footer_text))



# run the app.
if __name__ == "__main__":
    # Setting debug to True enables debug output. This line should be
    # removed before deploying a production app.
    application.debug = True
    application.run()
