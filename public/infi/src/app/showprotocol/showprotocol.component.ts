import { Component, OnInit } from '@angular/core';
import { RestService } from '../rest.service';
import { CategoryPipe } from './category.pipe';
import {FilterPipe} from './filter.pipe';
import { Pipe, PipeTransform } from '@angular/core';
import { Jsonp } from '../../../node_modules/@angular/http';

@Component({
  selector: 'app-showprotocol',
  templateUrl: './showprotocol.component.html',
  styleUrls: ['./showprotocol.component.css']
})
@Pipe({
  name: 'FilterPipe',
})

export class ShowprotocolComponent implements OnInit {
  private protocol = [];

  records: Array<any>=new Array();
  isDesc: boolean = false;
  column: string = 'CategoryName';

  constructor(
    public rest: RestService
  ){ }

  ngOnInit(): void {
    this.rest.showprotocol().subscribe(data=>{
        this.protocol = data;

        for(var i=0; i<this.protocol.length;i++){
            console.log(this.protocol[i].title)
            console.log(this.protocol[i].beschreibung)
            const myObj = {Titel: this.protocol[i].title, Datum: this.protocol[i].s_date.slice(0,16),  Beschreibung: this.protocol[i].beschreibung};
            this.records.push(myObj);
            console.log(myObj);
            console.log(this.records[i].Titel);
        }
        //this.records= [JSON.parse(JSON.stringify(myObj))];
          /*  { },
            { CategoryID: 2,  CategoryName: "Condiments", Description: "Sweet and savory sauces" },
            { CategoryID: 3,  CategoryName: "Confections", Description: "Desserts and candies" },
            { CategoryID: 4,  CategoryName: "Cheeses",  Description: "Smetana, Quark and Cheddar Cheese" },
            { CategoryID: 5,  CategoryName: "Grains/Cereals", Description: "Breads, crackers, pasta, and cereal" },
            { CategoryID: 6,  CategoryName: "Beverages", Description: "Beers, and ales" },
            { CategoryID: 7,  CategoryName: "Condiments", Description: "Selishes, spreads, and seasonings" },
            { CategoryID: 8,  CategoryName: "Confections", Description: "Sweet breads" },
            { CategoryID: 9,  CategoryName: "Cheeses",  Description: "Cheese Burger" },
            { CategoryID: 10, CategoryName: "Grains/Cereals", Description: "Breads, crackers, pasta, and cereal" }
           ]*/
      
          // console.log(this.protocol[0]);
    });

     //this.records = ;
     // this.sort(this.column);
  }

  // Declare local variable
  direction: number;
  // Change sort function to this: 
  sort(property){
    this.isDesc = !this.isDesc; //change the direction    
    this.column = property;
    let direction = this.isDesc ? 1 : -1;

    this.records.sort(function(a, b){
        if(a[property] < b[property]){
            return -1 * direction;
        }
        else if( a[property] > b[property]){
            return 1 * direction;
        }
        else{
            return 0;
        }
    });
};
}
//type ProtocolType = {CategoryID: string,  CategoryName: string, Description: string};
