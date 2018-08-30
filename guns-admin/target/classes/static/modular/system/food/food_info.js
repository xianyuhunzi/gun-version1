/**
 * 初始化food详情对话框
 */
var FoodInfoDlg = {
    foodInfoData : {}
};

/**
 * 清除数据
 */
FoodInfoDlg.clearData = function() {
    this.foodInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FoodInfoDlg.set = function(key, val) {
    this.foodInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
FoodInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
FoodInfoDlg.close = function() {
    parent.layer.close(window.parent.Food.layerIndex);
}

/**
 * 收集数据
 */
FoodInfoDlg.collectData = function() {
    this
    .set('id')
    .set('number')
    .set('name')
    .set('price')
    .set('productdate')
    .set('expirationdate')
    .set('stock')
    .set('unit')
    .set('status')
    .set('addtime')
    .set('updatetime')
    .set('addperson')
    .set('description')
    .set('remark');
}

/**
 * 提交添加
 */
FoodInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/food/add", function(data){
        Feng.success("添加成功!");
        window.parent.Food.table.refresh();
        FoodInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.foodInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
FoodInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/food/update", function(data){
        Feng.success("修改成功!");
        window.parent.Food.table.refresh();
        FoodInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.foodInfoData);
    ajax.start();
}

$(function() {

});
