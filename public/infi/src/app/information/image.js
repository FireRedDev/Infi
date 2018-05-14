<<<<<<< HEAD
window.onload = function() {
  debugger;
 var fileInput = document.getElementById('fileInput');
    var fileDisplayArea = document.getElementById('fileDisplayArea');

    fileInput.addEventListener('change', function(e) {
	var file = fileInput.files[0];
	var imageType = /image.*/;
    if (file.type.match(imageType)) {
		var reader = new FileReader();

		reader.onload = function(e) {
		fileDisplayArea.innerHTML = "";

		// Create a new image.
		var img = new Image();
		// Set the img src property using the data URL.
    img.src = reader.result;
    debugger;
		dataURItoBlob(reader.result);

		// Add the image to the page.
		fileDisplayArea.appendChild(img);
	  }

	  reader.readAsDataURL(file); 
	} else {
	  fileDisplayArea.innerHTML = "File not supported!";
	}
    });


}
function dataURItoBlob(dataURI) {
  // convert base64 to raw binary data held in a string
  // doesn't handle URLEncoded DataURIs - see SO answer #6850276 for code that does this
  var byteString = atob(dataURI.split(',')[1]);

  // separate out the mime component
  var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

  // write the bytes of the string to an ArrayBuffer
  var ab = new ArrayBuffer(byteString.length);

  // create a view into the buffer
  var ia = new Uint8Array(ab);

  // set the bytes of the buffer to the correct values
  for (var i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
  }

  // write the ArrayBuffer to a blob, and you're done
  var blob = new Blob([ab], {type: mimeString});
  debugger;
  return blob;

=======
window.onload = function() {
  debugger;
 var fileInput = document.getElementById('fileInput');
    var fileDisplayArea = document.getElementById('fileDisplayArea');

    fileInput.addEventListener('change', function(e) {
	var file = fileInput.files[0];
	var imageType = /image.*/;
    if (file.type.match(imageType)) {
		var reader = new FileReader();

		reader.onload = function(e) {
		fileDisplayArea.innerHTML = "";

		// Create a new image.
		var img = new Image();
		// Set the img src property using the data URL.
    img.src = reader.result;
    debugger;
		dataURItoBlob(reader.result);

		// Add the image to the page.
		fileDisplayArea.appendChild(img);
	  }

	  reader.readAsDataURL(file); 
	} else {
	  fileDisplayArea.innerHTML = "File not supported!";
	}
    });


}
function dataURItoBlob(dataURI) {
  // convert base64 to raw binary data held in a string
  // doesn't handle URLEncoded DataURIs - see SO answer #6850276 for code that does this
  var byteString = atob(dataURI.split(',')[1]);

  // separate out the mime component
  var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]

  // write the bytes of the string to an ArrayBuffer
  var ab = new ArrayBuffer(byteString.length);

  // create a view into the buffer
  var ia = new Uint8Array(ab);

  // set the bytes of the buffer to the correct values
  for (var i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
  }

  // write the ArrayBuffer to a blob, and you're done
  var blob = new Blob([ab], {type: mimeString});
  debugger;
  return blob;

>>>>>>> information_einfügen_client
}