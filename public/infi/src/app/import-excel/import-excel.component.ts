import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-import-excel',
  templateUrl: './import-excel.component.html',
  styleUrls: ['./import-excel.component.css']
})
export class ImportExcelComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
 
    activeColor: string = 'green';
    baseColor: string = '#ccc';
    overlayColor: string = 'rgba(255,255,255,0.5)';
    
    dragging: boolean = false;
    loaded: boolean = false;
    imageLoaded: boolean = false;
    imageSrc: string = '../../assets/upload.png';
    
    
    handleImageLoad() {
        this.imageLoaded = true;
    }

    handleInputChange(e) {
        this.imageSrc = '../../assets/csv.png';        
    }
   
}
