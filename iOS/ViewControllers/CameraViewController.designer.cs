// WARNING
//
// This file has been generated automatically by Xamarin Studio from the outlets and
// actions declared in your storyboard file.
// Manual changes to this file will not be maintained.
//
using Foundation;
using System;
using System.CodeDom.Compiler;
using UIKit;

namespace AND101.iOS
{
    [Register ("CameraViewController")]
    partial class CameraViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton flashButton { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIView liveCameraStream { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton switchCameraButton { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton triggerCameraButton { get; set; }

        [Action ("FlashButtonTapped:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void FlashButtonTapped (UIKit.UIButton sender);

        [Action ("SwitchCameraButtonTapped:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void SwitchCameraButtonTapped (UIKit.UIButton sender);

        [Action ("TakePhoto:")]
        [GeneratedCode ("iOS Designer", "1.0")]
        partial void TakePhoto (UIKit.UIButton sender);

        void ReleaseDesignerOutlets ()
        {
            if (flashButton != null) {
                flashButton.Dispose ();
                flashButton = null;
            }

            if (liveCameraStream != null) {
                liveCameraStream.Dispose ();
                liveCameraStream = null;
            }

            if (switchCameraButton != null) {
                switchCameraButton.Dispose ();
                switchCameraButton = null;
            }

            if (triggerCameraButton != null) {
                triggerCameraButton.Dispose ();
                triggerCameraButton = null;
            }
        }
    }
}