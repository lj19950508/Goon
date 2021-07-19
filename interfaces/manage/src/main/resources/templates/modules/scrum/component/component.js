<script>
$(function () {

    $("#input-id").fileinput({
        // language: 'en',
        // showCaption: true,//展示说明文字？
        // showBrowse: true,展示上传按钮
        // showPreview 显示预览
        //        showRemove: true, 显示删除按钮
        //        showUpload: true,显示上传按钮
        //        showUploadStats: true, 显示上传统计
        //         showCancel: null, 显示取消按钮
        //         showPause: null,显示暂停
        //         showClose: true, 显示关闭按钮
        //显示已上传缩略图         showUploadedThumbs: true,
        //showConsoleLogs显示日志
        //browseOnZoneClick?
        //autoReplace自动替换
        //autoOrientImage：function 自动朝向
        //autoOrientImageInitial?
        //required?
        // rtl: false,
        // hideThumbnailContent: false,
        // encodeUrl: true,
        // focusCaptionOnBrowse: true,
        // focusCaptionOnClear: true,
        // generateFileId: null,
        // previewClass: '',
        // captionClass: '',
        // frameClass: 'krajee-default',
        // mainClass: '',
        // mainTemplate: null,
        // fileSizeGetter: null,
        // initialCaption: '',
        // initialPreview: [],
        // initialPreviewDelimiter: '*$$*',
        // initialPreviewAsData: false,
        // initialPreviewFileType: 'image',
        // initialPreviewConfig: [],
        // initialPreviewThumbTags: [],
        // previewThumbTags: {},
        // initialPreviewShowDelete: true,
        // initialPreviewDownloadUrl: '',
        // removeFromPreviewOnError: false,
        // deleteUrl: '',
        // deleteExtraData: {},
        // overwriteInitial: true,
        // sanitizeZoomCache: function (content) {
        //     var $container = $h.createElement(content);
        //     $container.find('input,textarea,select,datalist,form,.file-thumbnail-footer').remove();
        //     return $container.html();
        // },
        // previewZoomButtonIcons: {
        //     prev: '<i class="bi-caret-left-fill"></i>',
        //     next: '<i class="bi-caret-right-fill"></i>',
        //     toggleheader: '<i class="bi-arrows-expand"></i>',
        //     fullscreen: '<i class="bi-arrows-fullscreen"></i>',
        //     borderless: '<i class="bi-arrows-angle-expand"></i>',
        //     close: '<i class="bi-x-lg"></i>'
        // },
        // previewZoomButtonClasses: {
        //     prev: 'btn btn-navigate',
        //     next: 'btn btn-navigate',
        //     toggleheader: defBtnCss1,
        //     fullscreen: defBtnCss1,
        //     borderless: defBtnCss1,
        //     close: defBtnCss1
        // },
        // previewTemplates: {},
        // previewContentTemplates: {},
        // preferIconicPreview: false,
        // preferIconicZoomPreview: false,
        // allowedFileTypes: null,
        // allowedFileExtensions: null,
        // allowedPreviewTypes: undefined,
        // allowedPreviewMimeTypes: null,
        // allowedPreviewExtensions: null,
        // disabledPreviewTypes: undefined,
        // disabledPreviewExtensions: ['msi', 'exe', 'com', 'zip', 'rar', 'app', 'vb', 'scr'],
        // disabledPreviewMimeTypes: null,
        // defaultPreviewContent: null,
        // customLayoutTags: {},
        // customPreviewTags: {},
        // previewFileIcon: '<i class="bi-file-earmark-fill"></i>',
        // previewFileIconClass: 'file-other-icon',
        // previewFileIconSettings: {},
        // previewFileExtSettings: {},
        // buttonLabelClass: 'hidden-xs',
        // browseIcon: '<i class="bi-folder2-open"></i> ',
        // browseClass: 'btn btn-primary',
        // removeIcon: '<i class="bi-trash"></i>',
        // removeClass: defBtnCss2,
        // cancelIcon: '<i class="bi-slash-circle"></i>',
        // cancelClass: defBtnCss2,
        // pauseIcon: '<i class="bi-pause-fill"></i>',
        // pauseClass: defBtnCss2,
        // uploadIcon: '<i class="bi-upload"></i>',
        // uploadClass: defBtnCss2,
        // uploadUrl: null,
        // uploadUrlThumb: null,
        // uploadAsync: true,
        // uploadParamNames: {
        //     chunkCount: 'chunkCount',
        //     chunkIndex: 'chunkIndex',
        //     chunkSize: 'chunkSize',
        //     chunkSizeStart: 'chunkSizeStart',
        //     chunksUploaded: 'chunksUploaded',
        //     fileBlob: 'fileBlob',
        //     fileId: 'fileId',
        //     fileName: 'fileName',
        //     fileRelativePath: 'fileRelativePath',
        //     fileSize: 'fileSize',
        //     retryCount: 'retryCount'
        // },
        // maxAjaxThreads: 5,
        // fadeDelay: 800,
        // processDelay: 100,
        // bitrateUpdateDelay: 500,
        // queueDelay: 10, // must be lesser than process delay
        // progressDelay: 0, // must be lesser than process delay
        // enableResumableUpload: false,
        // resumableUploadOptions: {
        //     fallback: null,
        //     testUrl: null, // used for checking status of chunks/ files previously / partially uploaded
        //     chunkSize: 2 * 1024, // in KB
        //     maxThreads: 4,
        //     maxRetries: 3,
        //     showErrorLog: true,
        //     retainErrorHistory: true, // display complete error history always unless user explicitly resets upload
        //     skipErrorsAndProceed: false // when set to true, files with errors will be skipped and upload will continue with other files
        // },
        // uploadExtraData: {},
        // zoomModalHeight: 480,
        // minImageWidth: null,
        // minImageHeight: null,
        // maxImageWidth: null,
        // maxImageHeight: null,
        // resizeImage: false,
        // resizePreference: 'width',
        // resizeQuality: 0.92,
        // resizeDefaultImageType: 'image/jpeg',
        // resizeIfSizeMoreThan: 0, // in KB
        // minFileSize: -1,
        // maxFileSize: 0,
        // maxFilePreviewSize: 25600, // 25 MB
        // minFileCount: 0,
        // maxFileCount: 0,
        // maxTotalFileCount: 0,
        // validateInitialCount: false,
        // msgValidationErrorClass: 'text-danger',
        // msgValidationErrorIcon: '<i class="bi-exclamation-circle-fill"></i> ',
        // msgErrorClass: 'file-error-message',
        // progressThumbClass: 'progress-bar progress-bar-striped active progress-bar-animated',
        // progressClass: 'progress-bar bg-success progress-bar-success progress-bar-striped active progress-bar-animated',
        // progressInfoClass: 'progress-bar bg-info progress-bar-info progress-bar-striped active progress-bar-animated',
        // progressCompleteClass: 'progress-bar bg-success progress-bar-success',
        // progressPauseClass: 'progress-bar bg-primary progress-bar-primary progress-bar-striped active progress-bar-animated',
        // progressErrorClass: 'progress-bar bg-danger progress-bar-danger',
        // progressUploadThreshold: 99,
        // previewFileType: 'image',
        // elCaptionContainer: null,
        // elCaptionText: null,
        // elPreviewContainer: null,
        // elPreviewImage: null,
        // elPreviewStatus: null,
        // elErrorContainer: null,
        // errorCloseButton: undefined,
        // slugCallback: null,
        // dropZoneEnabled: true,
        // dropZoneTitleClass: 'file-drop-zone-title',
        // fileActionSettings: {},
        // otherActionButtons: '',
        // textEncoding: 'UTF-8',
        // preProcessUpload: null,
        // ajaxSettings: {},
        // ajaxDeleteSettings: {},
        // showAjaxErrorDetails: true,
        // mergeAjaxCallbacks: false,
        // mergeAjaxDeleteCallbacks: false,
        // retryErrorUploads: true,
        // reversePreviewOrder: false,
        // usePdfRenderer: function () {
        //     var isIE11 = !!window.MSInputMethodContext && !!document.documentMode;
        //     return !!navigator.userAgent.match(/(iPod|iPhone|iPad|Android)/i) || isIE11;
        // },
        // pdfRendererUrl: '',
        // pdfRendererTemplate: '<iframe ' + IFRAME_ATTRIBS + '></iframe>',
        // tabIndexConfig: {
        //     browse: 500,
        //     remove: 500,
        //     upload: 500,
        //     cancel: null,
        //     pause: null,
        //     modal: -1
        // }



        browseOnZoneClick: true,
        showPreview:true,
        minFileCount: 1,
        //上传地址
        uploadUrl: "/file/upload",
        //是否允许暂停上传
        // enableResumableUpload: true,
        //断电续传选项
        // resumableUploadOptions: {
            // uncomment below if you wish to test the file for previous partial uploaded chunks
            // to the server and resume uploads from that point afterwards
            // testUrl: "http://localhost/test-upload.php"
        // },
        //上传额外参数
        uploadExtraData: {
            'uploadToken': 'SOME-TOKEN', // for access control / security
        },
        //最大文件数
        maxFileCount: 5,
        //允许文件后缀
        allowedFileTypes: ['image'],    // allow only images
        //显示取消按钮
        showCancel: true,
        //初始化预览图成DAA
        initialPreviewAsData: true,
        //重写首个？？
        overwriteInitial: false,
        //初始化图片
        // initialPreview: [],          // if you have previously uploaded preview files
        // initialPreviewConfig: [],    // if you have previously uploaded preview files
        //主题
        theme: 'fas',
        //删除地址
        deleteUrl: "http://localhost/file-delete.php"
        //事件
    }).on('fileuploaded', function(event, previewId, index, fileId) {
        console.log('File Uploaded', 'ID: ' + fileId + ', Thumb ID: ' + previewId);
    }).on('fileuploaderror', function(event, data, msg) {
        console.log('File Upload Error', 'ID: ' + data.fileId + ', Thumb ID: ' + data.previewId);
    }).on('filebatchuploadcomplete', function(event, preview, config, tags, extraData) {
        console.log('File Batch Uploaded', preview, config, tags, extraData);
    });
})
</script>