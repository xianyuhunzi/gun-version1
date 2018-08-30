/**
 * food管理初始化
 */
var Food = {
    id: "FoodTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Food.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
        {title: '商品编号，UUID生成，唯一', field: 'number', visible: true, align: 'center', valign: 'middle'},
        {title: '商品名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '价格', field: 'price', visible: true, align: 'center', valign: 'middle'},
        {title: '生产日期', field: 'productdate', visible: true, align: 'center', valign: 'middle'},
        {title: '保质期  单位：天', field: 'expirationdate', visible: true, align: 'center', valign: 'middle'},
        {title: '库存', field: 'stock', visible: true, align: 'center', valign: 'middle'},
        {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
        {
            title: '状态：1.上架  2.下架',
            field: 'status',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var a="";
                if (value == 1) {
                    var a = '<span style="color:greenyellow">上架</span>';
                } else {
                    var a = '<span style="color:red">下架</span>';
                }
                return a;
            }
        },
        {title: '添加时间', field: 'addtime', visible: true, align: 'center', valign: 'middle'},
        {title: '最后更新时间', field: 'updatetime', visible: true, align: 'center', valign: 'middle'},
        {title: '添加人', field: 'addperson', visible: true, align: 'center', valign: 'middle'},
        {title: '描述', field: 'description', visible: true, align: 'center', valign: 'middle'},
        {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Food.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Food.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加food
 */
Food.openAddFood = function () {
    var index = layer.open({
        type: 2,
        title: '添加food',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/food/food_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看food详情
 */
Food.openFoodDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'food详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/food/food_update/' + Food.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除food
 */
Food.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/food/delete", function (data) {
            Feng.success("删除成功!");
            Food.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("foodId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询food列表
 */
Food.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['name'] = $("#condition").val();
    Food.table.refresh({query: queryData});
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
Food.formParams = function () {
    var queryData = {};

    queryData['name'] = $("#condition").val();
    // queryData['logType'] = $("#logType").val();

    return queryData;
}

$(function () {
    var defaultColunms = Food.initColumn();
    var exportOpetions={};
    var exportOpetion={};
    exportOpetions.exportDataType="all";  //导出表格方式（默认basic：只导出当前页的表格数据；all：导出所有数据；selected：导出选中的数据）
    exportOpetions.exportTypes =['excel'] // sql, excel,pdf
    exportOpetions.showExport=true; //显示excel
    exportOpetion.fileName="食品表";
    exportOpetion.ignoreColumn=[0,8,9];
    exportOpetion.worksheetName="sheet1";
    exportOpetion.excelstyles= ['background-color', 'color', 'font-size', 'font-weight'];
    exportOpetion.tableName= '选手信息';
    exportOpetions.exportOpetion=exportOpetion
    var table = new BSTable(Food.id, "/food/list", defaultColunms,exportOpetions);
    table.setPaginationType("server");
    table.setQueryParams(Food.formParams());
    Food.table = table.init();
});
