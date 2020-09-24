import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from './../user.service';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {
  videos: any;
  constructor(private route:ActivatedRoute,private router: Router, private service: UserService) { 
    this.videos = {video1: 'assets/Videos/ATH1.mp4', video2: 'assets/Videos/ATH3.mp4'};
    
  }
  ngOnInit(): void {
  }

}