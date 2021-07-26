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
        engine:null,
        source: null,
        layer:null,
        snap:null,
        modify:null,
        enable:function(map){
            this.mapRef=map;
            this.source= new ol.source.Vector();
            this.layer = new ol.layer.Vector({
                source: this.source
            })
            this.layer.setMap(this.mapRef);
        },
        point: function () {
            this.engine =  new ol.interaction.Draw({
                source: this.source,
                type: 'Point'
            });
            this.mapRef.addInteraction(this.engine)
            this.snap = new ol.interaction.Snap({
                source: this.source
            })
            map.addInteraction(this.snap);
            this.modify = new ol.interaction.Modify({
                source:this.source
            })

            this.mapRef.addInteraction(this.modify)

        },
        line:function(){
            this.engine =  new ol.interaction.Draw({
                source: this.source,
                type: 'LineString'
            });
            this.mapRef.addInteraction(this.engine)
            this.snap = new ol.interaction.Snap({
                source: this.source
            })
            map.addInteraction(this.snap);
            this.modify = new ol.interaction.Modify({
                source:this.source
            })
            this.mapRef.addInteraction(this.modify)

        },
        polygon:function(){
            this.engine =  new ol.interaction.Draw({
                source: this.source,
                type: 'Polygon'
            });
            this.mapRef.addInteraction(this.engine)
            this.modify = new ol.interaction.Modify({
                source:this.source
            })
            this.snap = new ol.interaction.Snap({
                source: this.source
            })
            map.addInteraction(this.snap);
            this.mapRef.addInteraction(this.modify)

        },
        circle:function(){
            this.engine =  new ol.interaction.Draw({
                source: this.source,
                type: 'Circle',
            });
            this.mapRef.addInteraction(this.draw)
            this.modify = new ol.interaction.Modify({
                source:this.source
            })
            this.snap = new ol.interaction.Snap({
                source: this.source
            })
            map.addInteraction(this.snap);
            this.mapRef.addInteraction(this.modify)
        },
        stopDraw:function(){
            this.mapRef.removeInteraction(this.engine);
            this.mapRef.removeInteraction(this.engine);
        }
    },
    transformCoord(coord,source,target){
        return ol.proj.transform(coord, source, target)
    }
}