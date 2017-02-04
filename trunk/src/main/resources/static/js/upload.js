$(function() {
	$('#file_upload').uploadify({
		'swf' : '/js/uploadify/uploadify.swf',
		//'uploader' : '/js/uploadify/uploadify.php'
		'uploader'       : '/upload',  
		'fileObjName' : 'file',
		'buttonText': '<div>上传文件</div>'
		
		
	// Your options here
	});
});