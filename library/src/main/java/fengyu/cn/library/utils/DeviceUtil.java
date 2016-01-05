package fengyu.cn.library.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

/**
 * �ֻ�ƽ̨����Symbian��Android��iPhone����汾�ֻ�IMEI �ֻ�IMSI �ֻ���ƷID���ֻ��ͺţ�
 * ����ID��mcc�й�60��Ӫ��ID��MNC�й��ƶ�0,02 (GSM),07 (TD)����ͨ��01
 * ����ID��λ�������룬����Ψһ��ʶ���ҹ�����PLMN��ÿ��λ�����ģ���һ�ֽ�16���Ƶ�BCD�룩 ��ǰ��վID
 * 
 */

public class DeviceUtil {
	private static final String PLATFORM = "android";
	private static final int lOCAL_LANGUAGE_CN = 0;
	private static final int lOCAL_LANGUAGE_OTHER = 10000;

	public static String getPlatform() {
		return PLATFORM;
	}

	public static String getNativePhoneNumber(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String nativePhoneNumber = null;
		nativePhoneNumber = telephonyManager.getLine1Number();
		if (nativePhoneNumber != null) {
			// //�������ֻ����볤�Ƚ�ȡ+86,ʣ��11λ
			if (nativePhoneNumber.length() > 11)
				nativePhoneNumber = nativePhoneNumber.substring(3);
		}
		return nativePhoneNumber;
	}

	public static String getPhoneNumber(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String NativePhoneNumber = null;
		NativePhoneNumber = telephonyManager.getLine1Number();
		return NativePhoneNumber;
	}

	public static String getDeviceId(Context context) {
		String imei = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
		if (TextUtils.isEmpty(imei)) {
			imei = "";
		}
		return imei;
	}

	public static String getIMSI(Context context) {
		String imsi = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
		if (TextUtils.isEmpty(imsi)) {
			imsi = "";
		}
		return imsi;
	}

	public static String getProductId() {
		return Build.MODEL;
	}

	public static String getSDKVersion() {
		return Build.VERSION.SDK;
	}

	public static String getSDKLevel() {
		return Build.VERSION.RELEASE;
	}

	// location code
	public static String getLac(Context context) {
		String ret = null;
		CellLocation loc = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
		if (loc != null && loc instanceof GsmCellLocation) {
			GsmCellLocation gsmLoc = (GsmCellLocation) loc;
			ret = String.valueOf(gsmLoc.getLac());
		} else if (loc != null && loc instanceof CdmaCellLocation) {
			CdmaCellLocation cdmaLoc = (CdmaCellLocation) loc;
			ret = String.valueOf(cdmaLoc.getBaseStationLatitude());
		}
		return ret;
	}

	public static String getCellId(Context context) {
		String ret = null;
		CellLocation loc = ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getCellLocation();
		if (loc != null && loc instanceof GsmCellLocation) {
			GsmCellLocation gsmLoc = (GsmCellLocation) loc;
			ret = String.valueOf(gsmLoc.getCid());
		} else if (loc != null && loc instanceof CdmaCellLocation) {
			CdmaCellLocation cdmaLoc = (CdmaCellLocation) loc;
			ret = String.valueOf(cdmaLoc.getBaseStationId());
		}
		return ret;
	}

	public static String getWlanMac(Context c) {
		WifiManager mWifiManager = (WifiManager) c
				.getSystemService(Context.WIFI_SERVICE);
		return mWifiManager.getConnectionInfo().getBSSID();
	}

	public static String getVersion(Context context)// ��ȡ�汾��
	{
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
}
