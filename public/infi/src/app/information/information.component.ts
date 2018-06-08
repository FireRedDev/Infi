import { Component, OnInit, Input, Output } from '@angular/core';
import { Info } from '../models/info';
import { RestService } from '../rest.service';
import { jrkEntitaet } from '../models/jrkEntitaet.model';
import { EventEmitter } from '@angular/core';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-information',
  templateUrl: './information.component.html',
  styleUrls: ['./information.component.css']
})
export class InformationComponent implements OnInit {
  @Input() jrkEntitaet: jrkEntitaet;
  @Output() changeView: EventEmitter<string> = new EventEmitter();

  fileInput;
  fileDisplayArea;
  imgpath;
  file;
  fileerror=false;

  success=false;
    constructor(private rest: RestService) {
      this.rest=rest;
     }
    de: any;
  
      ngOnInit() {  
        this.fileDisplayArea="";  

    }

    save(){
      var imageType = /image.*/;
      debugger;
      for (var i=0; i<this.file.length;i++) {
        var file=this.fileInput.files[i]
        if(file.type.match(imageType)&&this.file[i].size<1097152){
          this.actInformation.mediapath.push("http://localhost:8080/upload_image/"+this.file[i].name);
        }
      }
        this.rest.insertInfo(this.jrkEntitaet,this.actInformation)
          .subscribe(data => {
            console.log("home");
            this.changeView.emit("home");
        });
        this.success=true;
    }
    
      actInformation:Info = new Info(0,'','',[],'');
      submitted = false;
   
      onSubmit() { this.submitted = true; }
      
          
      fileUpload(e) {
        this.fileInput = document.getElementById('fileInput');
        this.file=this.fileInput.files
        for (var i=0; i<this.fileInput.files.length;i++) {
          var file=this.fileInput.files[i]
          console.log("File"+file)
          var rest = this.rest;
          var imageType = /image.*/;
    
          if (file.type.match(imageType)&&this.file[i].size<1097152) {
            var reader = new FileReader();
    
            reader.onload = function(e) {
              var dataURI =reader .result;
              console.log(dataURI);
              //https://stackoverflow.com/questions/12168909/blob-from-dataurl
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
              console.log(blob);
              rest.uploadImage(blob,file.name)
                .subscribe(data => {
                  console.log("insertImage")
                  alert("Bild hochgeladen")
              });
          
            }
    
          reader.readAsDataURL(file);
        } else {
          this.fileDisplayArea = "File not supported!";
          this.fileerror=true;
        }
      }
    }

}
