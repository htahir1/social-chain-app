using Foundation;
using System;
using UIKit;

namespace AND101.iOS
{
	public partial class CameraViewController : UIViewController
	{
		public CameraViewController(IntPtr handle) : base(handle)
		{
		}

		partial void TakePhoto(UIButton sender)
		{
			Console.WriteLine("Taking Photo");
		}
	}
}