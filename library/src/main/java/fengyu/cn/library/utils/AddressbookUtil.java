package fengyu.cn.library.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

public class AddressbookUtil {

	public static Contact getContact(Context context, Uri uri) {
		Contact contact = new Contact();
		ContentResolver cr = context.getContentResolver();
		Cursor phone = null;
		// ȡ�õ绰���п�ʼһ��Ĺ��
		Cursor cursor = cr.query(uri, null, null, null, null);
		try {
			if (cursor != null) {
				cursor.moveToFirst();
				// ȡ����ϵ������
				int nameFieldColumnIndex = cursor
						.getColumnIndex(PhoneLookup.DISPLAY_NAME);
				contact.setName(cursor.getString(nameFieldColumnIndex));

				// ȡ�õ绰����
				String ContactId = cursor.getString(cursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				phone = cr.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ "=" + ContactId, null, null);

				if (phone != null) {
					phone.moveToFirst();
					contact.setPhone(phone.getString(phone
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

				}
			}
		} catch (Exception e) {
			phone.close();
			cursor.close();
		}

		return contact;
	}

	public static class Contact {
		String name = "";
		String phone = "";

		public String getName() {
			return name;
		}

		public String getPhone() {
			return phone;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}
	}

}
