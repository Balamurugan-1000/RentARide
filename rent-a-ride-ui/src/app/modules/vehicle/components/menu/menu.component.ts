import { Component, Inject, OnInit } from '@angular/core';
import {DOCUMENT, NgClass} from '@angular/common';
import { Router, NavigationEnd, RouterModule } from '@angular/router';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [RouterModule, NgClass],
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {
  isNavbarOpen = false; // Track the state of the navbar (open/closed)

  constructor(
    @Inject(DOCUMENT) private document: Document,
    private router: Router
  ) { }

  logout(): void {
    localStorage.clear();
    window.location.href = '/login'; // Redirect to login page
  }

  ngOnInit(): void {
    const linkColor = this.document.querySelectorAll('.nav-link');

    // Highlight the active link on component initialization
    this.updateActiveLink();

    // Add click event listeners to update active links dynamically
    linkColor.forEach(link => {
      link.addEventListener('click', () => {
        linkColor.forEach(l => l.classList.remove('active'));
        link.classList.add('active');
      });
    });

    // Listen to route changes and update the active link
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateActiveLink();
      }
    });
  }

  private updateActiveLink(): void {
    const linkColor = this.document.querySelectorAll('.nav-link');
    const currentRoute = this.router.url;

    linkColor.forEach(link => {
      const href = link.getAttribute('href') || '';
      if (currentRoute === href) {
        link.classList.add('active');
      } else {
        link.classList.remove('active');
      }
    });
  }

  // Toggle navbar for mobile view
  toggleNavbar(): void {
    this.isNavbarOpen = !this.isNavbarOpen;
  }

  // Close the navbar after clicking a link
  closeNavbar(): void {
    this.isNavbarOpen = false;
  }
}
