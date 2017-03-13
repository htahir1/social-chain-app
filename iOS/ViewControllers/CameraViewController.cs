using System;
using UIKit;
using Foundation;
using AVFoundation;
using System.Threading.Tasks;

namespace AND101.iOS
{
	public partial class CameraViewController : UIViewController
	{
		bool flashOn = false;

		AVCaptureSession captureSession;
		AVCaptureDeviceInput captureDeviceInput;
		AVCaptureStillImageOutput stillImageOutput;
		AVCaptureVideoPreviewLayer videoPreviewLayer;

		public CameraViewController(IntPtr handle) : base(handle)
		{

		}

		public override async void ViewDidLoad()
		{
			base.ViewDidLoad();

			triggerCameraButton.BackgroundColor = UIColor.White;
			triggerCameraButton.Layer.CornerRadius = triggerCameraButton.Frame.Width / 2;

			switchCameraButton.BackgroundColor = UIColor.White;
			switchCameraButton.Layer.CornerRadius = switchCameraButton.Frame.Width / 4;

			flashButton.BackgroundColor = UIColor.White;
			flashButton.Layer.CornerRadius = flashButton.Frame.Width / 4;

			await AuthorizeCameraUse();
			SetupLiveCameraStream();
		}

		public override void DidReceiveMemoryWarning()
		{
			base.DidReceiveMemoryWarning();
		}

		async partial void TakePhoto(UIButton sender)
		{
			Console.WriteLine("Taking Photo");

			var videoConnection = stillImageOutput.ConnectionFromMediaType(AVMediaType.Video);
			var sampleBuffer = await stillImageOutput.CaptureStillImageTaskAsync(videoConnection);

			var jpegImageAsNsData = AVCaptureStillImageOutput.JpegStillToNSData(sampleBuffer);
			var jpegAsByteArray = jpegImageAsNsData.ToArray();

			// TODO: Send this to local storage or cloud storage such as Azure Storage.
		}

		partial void SwitchCameraButtonTapped(UIButton sender)
		{
			var devicePosition = captureDeviceInput.Device.Position;
			if (devicePosition == AVCaptureDevicePosition.Front)
			{
				devicePosition = AVCaptureDevicePosition.Back;
			}
			else
			{
				devicePosition = AVCaptureDevicePosition.Front;
			}

			var device = GetCameraForOrientation(devicePosition);
			ConfigureCameraForDevice(device);

			captureSession.BeginConfiguration();
			captureSession.RemoveInput(captureDeviceInput);
			captureDeviceInput = AVCaptureDeviceInput.FromDevice(device);
			captureSession.AddInput(captureDeviceInput);
			captureSession.CommitConfiguration();
		}

		public AVCaptureDevice GetCameraForOrientation(AVCaptureDevicePosition orientation)
		{
			var devices = AVCaptureDevice.DevicesWithMediaType(AVMediaType.Video);

			foreach (var device in devices)
			{
				if (device.Position == orientation)
				{
					return device;
				}
			}

			return null;
		}

		partial void FlashButtonTapped(UIButton sender)
		{
			var device = captureDeviceInput.Device;

			var error = new NSError();
			if (device.HasFlash)
			{
				if (device.FlashMode == AVCaptureFlashMode.On)
				{
					Console.WriteLine("Turning flash OFF");
					device.LockForConfiguration(out error);
					device.FlashMode = AVCaptureFlashMode.Off;  // before iOS 10
					device.TorchMode = AVCaptureTorchMode.Off;  // after iOS 10
					device.UnlockForConfiguration();

					flashButton.SetImage(UIImage.FromBundle("FlashOn"), UIControlState.Normal);
				}
				else
				{
					Console.WriteLine("Turning flash ON");
					device.LockForConfiguration(out error);
					device.FlashMode = AVCaptureFlashMode.On;  // before iOS 10
					device.TorchMode = AVCaptureTorchMode.On;  // after iOS 10
					device.UnlockForConfiguration();

					flashButton.SetImage(UIImage.FromBundle("FlashOff"), UIControlState.Normal);
				}
			}

			flashOn = !flashOn;
		}

		async Task AuthorizeCameraUse()
		{
			var authorizationStatus = AVCaptureDevice.GetAuthorizationStatus(AVMediaType.Video);

			if (authorizationStatus != AVAuthorizationStatus.Authorized)
			{
				await AVCaptureDevice.RequestAccessForMediaTypeAsync(AVMediaType.Video);
			}
		}

		// TODO: make this a task to run in the background --> Speed up application launch
		public void SetupLiveCameraStream()
		{
			captureSession = new AVCaptureSession();

			var viewLayer = liveCameraStream.Layer;
			videoPreviewLayer = new AVCaptureVideoPreviewLayer(captureSession)
			{
				Frame = this.View.Frame
			};
			liveCameraStream.Layer.AddSublayer(videoPreviewLayer);

			var captureDevice = AVCaptureDevice.GetDefaultDevice(AVMediaType.Video);
			ConfigureCameraForDevice(captureDevice);
			captureDeviceInput = AVCaptureDeviceInput.FromDevice(captureDevice);
			captureSession.AddInput(captureDeviceInput);

			var dictionary = new NSMutableDictionary();
			dictionary[AVVideo.CodecKey] = new NSNumber((int)AVVideoCodec.JPEG);
			stillImageOutput = new AVCaptureStillImageOutput()
			{
				OutputSettings = new NSDictionary()
			};

			captureSession.AddOutput(stillImageOutput);
			captureSession.StartRunning();
		}

		void ConfigureCameraForDevice(AVCaptureDevice device)
		{
			var error = new NSError();
			if (device.IsFocusModeSupported(AVCaptureFocusMode.ContinuousAutoFocus))
			{
				device.LockForConfiguration(out error);
				device.FocusMode = AVCaptureFocusMode.ContinuousAutoFocus;
				device.UnlockForConfiguration();
			}
			else if (device.IsExposureModeSupported(AVCaptureExposureMode.ContinuousAutoExposure))
			{
				device.LockForConfiguration(out error);
				device.ExposureMode = AVCaptureExposureMode.ContinuousAutoExposure;
				device.UnlockForConfiguration();
			}
			else if (device.IsWhiteBalanceModeSupported(AVCaptureWhiteBalanceMode.ContinuousAutoWhiteBalance))
			{
				device.LockForConfiguration(out error);
				device.WhiteBalanceMode = AVCaptureWhiteBalanceMode.ContinuousAutoWhiteBalance;
				device.UnlockForConfiguration();
			}
		}
	}
}