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

  success=false;
    constructor(private rest: RestService) {
      this.rest=rest;
     }
    de: any;
  
      ngOnInit() {  
        this.fileDisplayArea="";  

    }

    save(){
      this.actInformation.mediapath.push("assets/upload/"+this.file.name);
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
        this.file = this.fileInput.files[0];
        var file = this.file;
        var rest = this.rest;
        var imageType = /image.*/;
  
        if (this.file.type.match(imageType)) {
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
                debugger;
                console.log("insertImage")
            });
            /*var http = new XMLHttpRequest();
            var url = "http://localhost:8080/upload?filename="+file.name;
            var params = blob;
            http.open("POST", url, true);
          
            //Send the proper header information along with the request
            http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
          
            http.onreadystatechange = function() {//Call a function when the state changes.
              if(http.readyState == 4 && http.status == 200) {
                debugger;
                console.log(http.responseText);
              }
            }
            http.send(params);*/            
          }
  
          reader.readAsDataURL(this.file);
        } else {
          this.fileDisplayArea = "File not supported!";
          console.log(this.fileDisplayArea);
        }
      }
 

}
