//req ol.js
var OlUtil = {
    wmts: {
        tiandituMap:function(type,key){
            return 'https://t{0-7}.tianditu.gov.cn/DataServer?T=' + type + '&x={x}&y={y}&l={z}&tk=' + key
        },
        googleMap:function(type){
            return 'http://www.google.cn/maps/vt?lyrs='+type+'@189&gl=cn&x={x}&y={y}&z={z}'
        },
        aMap:function(type){
            //6 卫星图 7路图地图 8路途路线图
            return 'http://wprd0{1-4}.is.autonavi.com/appmaptile?x={x}&y={y}&z={z}&lang=zh_cn&size=1&scl=1&style='+type;
        },

    },
    draw: {
        mapRef:null,
        tool:null,
        layer:new ol.layer.Vector({
            source:  new ol.source.Vector(),
        }),
        enable:function(map){
            this.mapRef=map;
            console.log(this.layer.setMap(map));
        },
        point: function () {
        },
        line:function(){
            this.tool =  new ol.interaction.Draw({
                source: this.source,
                type: 'LineString'
            });
            this.mapRef.addInteraction(this.tool)
        },
        polygon:function(){
            this.tool =  new ol.interaction.Draw({
                source: this.source,
                type: 'Polygon'
            });
            this.mapRef.addInteraction(this.tool)
        },
        circle:function(){
            this.tool =  new ol.interaction.Draw({
                source: this.source,
                type: 'Circle'
            });
            this.mapRef.addInteraction(this.tool)
        },
        stopDraw:function(){
            this.mapRef.removeInteraction(this.tool);
        }
    },
    transformCoord(coord,source,target){
        return ol.proj.transform(coord, source, target)
    }
}