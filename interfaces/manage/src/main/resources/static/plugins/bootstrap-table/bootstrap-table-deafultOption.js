var defaultTableOption = {
    locale: 'zh-CN',
    method: 'get',
    dataType: "json",
    contentType: "application/x-www-form-urlencoded",
    minimumCountColumns: 2,
    striped: false,
    fixedColumns: true, //
    fixedNumber: 1,
    fixedRightNumber: 1,
    cache: false,
    pagination: true,
    sidePagination: "server",
    sortOrder: "asc",

    pageList: [10, 25, 50, 100],
    responseHandler: function(res) {

        return {"total": res.data.totalElements, "rows": res.data.content};
    },
    queryParams : function(params) {

        var searchParam = $("#searchForm").serializeJSON();
        dealFormArray($("#searchForm"),searchParam);

        searchParam.page = params.limit === undefined? "1" :params.offset/params.limit;
        searchParam.size = params.limit === undefined? -1 : params.limit;
        if(this.sortName){
            searchParam.sort=this.sortName+","+this.sortOrder
        }

        return searchParam;
    },
}



var defaultTreeTableOption = {
    locale: 'zh-CN',
    method: 'get',
    dataType: "json",
    contentType: "application/x-www-form-urlencoded",
    minimumCountColumns: 2,
    striped: false,
    fixedColumns: true, //
    fixedNumber: 1,
    fixedRightNumber: 1,
    cache: false,
    pagination: false,
    sidePagination: "server",
    sortOrder: "asc",

    responseHandler: function(res) {
        return {"total": res.data.totalElements, "rows": res.data};
    },
    queryParams : function(params) {
        var searchParam = $("#searchForm").serializeJSON();
        // searchParam.page =defaultTreeTableOption  params.limit === undefined? "1" :params.offset/params.limit;
        // searchParam.size = params.limit === undefined? -1 : params.limit;
        return searchParam;
    },

}

var defaultSimpleTableOption = {
    locale: 'zh-CN',
    method: 'get',
    dataType: "json",
    contentType: "application/x-www-form-urlencoded",
    striped: false,
    cache: false,
}