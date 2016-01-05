package fengyu.cn.library.photopicker.event;


import fengyu.cn.library.photopicker.entity.Photo;

/**
 * Created by fys on 2015/9/6.
 */

public interface OnItemCheckListener {

  /***
   *
   * @param position ��ѡͼƬ��λ��
   * @param path     ��ѡ��ͼƬ
   *@param isCheck   ��ǰ״̬
   * @param selectedItemCount  ��ѡ����
   * @return enable check
   */
  boolean OnItemCheck(int position, Photo path, boolean isCheck, int selectedItemCount);

}
