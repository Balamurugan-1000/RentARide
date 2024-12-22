import { Component, Inject, OnInit } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'] // Fix typo `styleUrl` → `styleUrls`
})
export class MenuComponent implements OnInit {
  logout() {
    localStorage.clear();
    window.location.href = '/login';
  }

  constructor(
    @Inject(DOCUMENT) private document: Document
  ) { }

  ngOnInit(): void {
    const linkColor = this.document.querySelectorAll('.nav-link');
    linkColor.forEach(link => {
      const href = link.getAttribute('href') || '';


      // Add 'active' to other links based on their href
      if (link.classList.contains('first')) {
        link.classList.add('active');
      }

      // Add click event listener for dynamic behavior
      link.addEventListener('click', () => {
        // Remove 'active' from all links
        linkColor.forEach(l => l.classList.remove('active'));

        // Add 'active' to the clicked link
        link.classList.add('active');
      });
    });
  }



}
