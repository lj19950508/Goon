<script>

    $(function(){
        let map =new ol.Map({
            target: 'map',
            controls: ol.control.defaults({
                zoom: false,
                rotate: false
            }),
            interactions:new ol.interaction.defaults({
                doubleClickZoom:false,   //屏蔽双击放大事件
            }),
            layers:[
                new ol.layer.Tile({
                    source: new ol.source.XYZ({
                        url: OlUtil.wmts.aMap('6'),
                    })
                }),
                new ol.layer.Tile({
                    source: new ol.source.XYZ({
                        url: OlUtil.wmts.aMap('8'),
                    })
                })
            ],
            view: new ol.View({
                //坐标系
                center: OlUtil.transformCoord([118.680144,24.879428],'EPSG:4326', 'EPSG:3857'),
                zoom: 10,
            }),
        })

    })
</script>