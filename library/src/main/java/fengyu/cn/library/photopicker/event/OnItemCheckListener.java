package fengyu.cn.library.photopicker.event;


import fengyu.cn.library.photopicker.entity.Photo;

/**
 * Created by fys on 2015/9/6.
 */

public interface OnItemCheckListener {

  /***
   *
   * @param position 所选图片的位置
   * @param path     所选的图片
   *@param isCheck   当前状态
   * @param selectedItemCount  已选数量
   * @return enable check
   */
  boolean OnItemCheck(int position, Photo path, boolean isCheck, int selectedItemCount);

}
