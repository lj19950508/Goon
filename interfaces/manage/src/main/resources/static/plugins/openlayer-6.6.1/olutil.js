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
            this.stopDraw();
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
            return this.engine;
        },
        line:function(){
            this.stopDraw();
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
            return this.engine;
        },
        polygon:function(){
            this.stopDraw();
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
            return this.engine;
        },
        circle:function(){
            this.stopDraw();
            this.engine =  new ol.interaction.Draw({
                source: this.source,
                type: 'Circle',
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
            return this.engine;
        },
        stopDraw:function(){
            this.mapRef.removeInteraction(this.engine);
            this.mapRef.removeInteraction(this.modify);
            this.mapRef.removeInteraction(this.snap);
        }
    },
    measure:{
        mapRef:null,
        feature:null,
        measureTooltip:null,
        measureTooltipElement:null,
        helpTooltip:null,
        helpTooltipElement:null,
        listener:null,
        continuePolygonMsg:'Click to continue drawing the polygon',
        continueLineMsg:'Click to continue drawing the line',
        draw:null,
        enable:function(map,draw){
            this.mapRef=map;
            this.draw=draw;
        },
        polygon:function(){
            ol.Observable.unByKey(OlUtil.measure.lisenter);

            OlUtil.draw.stopDraw();

            let draw = OlUtil.draw.polygon();
            this.createMeasureTooltip();
            this.createHelpTooltip();

            this.mapRef.on('pointermove', this.pointerMoveHandler);
            draw.on('drawstart',this.drawStart)
            draw.on('drawend',this.drawEnd)
        },
        line:function(){
            ol.Observable.unByKey(OlUtil.measure.lisenter);
            OlUtil.draw.stopDraw();

            let draw = OlUtil.draw.line();
            this.createMeasureTooltip();
            this.createHelpTooltip();

            this.mapRef.on('pointermove', this.pointerMoveHandler);
            draw.on('drawstart',this.drawStart)
            draw.on('drawend',this.drawEnd)
        },
        circle:function(){

        },
        pointerMoveHandler:function(event){

            if(event.dragging){
                return;
            }
            let helpMsg = 'Click to start drawing';

            if(OlUtil.measure.feature){
                const gemo = sketch.getGeometry();
                if(gemo instanceof ol.geom.Polygon){
                    helpMsg= OlUtil.measure.continuePolygonMsg;
                }else if (gemo instanceof ol.geom.LineString){
                    helpMsg= OlUtil.measure.continueLineMsg;
                }
            }
            OlUtil.measure.helpTooltipElement.innerHTML = helpMsg;
            OlUtil.measure.helpTooltip.setPosition(event.coordinate);
            OlUtil.measure.helpTooltipElement.classList.remove('hidden');


        },
        drawStart:function(evt){
            this.feature = evt.feature;

            let tooltipCoord = evt.coordinate;
            this.lisenter = this.feature.getGeometry().on('change',function(e){
                const geom = e.target;
                let output;
                if(geom instanceof ol.geom.Polygon){
                    output = OlUtil.measure.formatArea(geom);
                    tooltipCoord = geom.getInteriorPoint().getCoordinates();
                }else if (geom instanceof ol.geom.LineString){
                    output = OlUtil.measure.formatLength(geom);
                    tooltipCoord = geom.getLastCoordinate();
                }
                OlUtil.measure.measureTooltipElement.innerHTML = output;
                OlUtil.measure.measureTooltip.setPosition(tooltipCoord);
            })
        },
        drawEnd:function(event){

            OlUtil.measure.measureTooltipElement.className = 'ol-tooltip ol-tooltip-static';
            OlUtil.measure.measureTooltip.setOffset([0, -7]);
            OlUtil.measure.feature = null;
            // unset tooltip so that a new one can be created
            OlUtil.measure.measureTooltipElement = null;
            OlUtil.measure.createMeasureTooltip();

        },
        formatLength:function(line){
            const length = ol.sphere.getLength(line)
            let output;
            if(length>100){
                output = Math.round((length / 1000) * 100) / 100 + ' ' + 'km';
            }else{
                output = Math.round(length * 100) / 100 + ' ' + 'm';
            }
            return output;
        },
        formatArea:function(polygon){
            const area = ol.sphere.getArea(polygon);
            let output;
            if (area > 10000) {
                output = Math.round((area / 1000000) * 100) / 100 + ' ' + 'km<sup>2</sup>';
            } else {
                output = Math.round(area * 100) / 100 + ' ' + 'm<sup>2</sup>';
            }
            return output;
        },
        createMeasureTooltip:function(){
            if (this.measureTooltipElement) {
                this.measureTooltipElement.parentNode.removeChild(this.measureTooltipElement);
            }
            this.measureTooltipElement = document.createElement('div');
            this.measureTooltipElement.className = 'ol-tooltip ol-tooltip-measure';
            this.measureTooltip = new ol.Overlay({
                element: this.measureTooltipElement,
                offset: [0, -15],
                positioning: 'bottom-center',
                stopEvent: false,
                insertFirst: false,
            });
            this.mapRef.addOverlay(this.measureTooltip);
        },
        createHelpTooltip:function(){
            if (this.helpTooltipElement) {
                this.helpTooltipElement.parentNode.removeChild(this.helpTooltipElement);
            }
            this.helpTooltipElement = document.createElement('div');
            this.helpTooltipElement.className = 'ol-tooltip hidden';
            this.helpTooltip = new ol.Overlay({
                element: this.helpTooltipElement,
                offset: [15, 0],
                positioning: 'center-left',
            });
            this.mapRef.addOverlay(this.helpTooltip);
        },
    },
    transformCoord(coord,source,target){
        return ol.proj.transform(coord, source, target)
    }
}