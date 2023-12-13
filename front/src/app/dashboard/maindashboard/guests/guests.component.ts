import { Component, OnInit } from '@angular/core';
import { Activity_area_enumMapping } from 'src/app/core/enum/ActivityArea.enum';
import { Profile_enumMapping } from 'src/app/core/enum/Profile_enum';
import { Workforce_enumMapping } from 'src/app/core/enum/Workforce.enum';
import { GuestModel } from '../../../core/models/Guest.model';
import { GuestService } from '../../../core/services/guest.service';

@Component({
  selector: 'app-guests',
  templateUrl: './guests.component.html',
  styleUrls: ['./guests.component.css'],
})
export class GuestsComponent implements OnInit {
  constructor(private guestService: GuestService) {}
  public workforce_enumMapping = Workforce_enumMapping;
  public profile_enumMapping = Profile_enumMapping;
  public activity_area_enumMapping = Activity_area_enumMapping;
  filteredItems!: GuestModel[];
  Guests: GuestModel[] = [];
  isLoading = false;

  modalStatus = false;

  openModal() {
    return (this.modalStatus = true);
  }

  ngOnInit(): void {
    this.isLoading = true;

    this.guestService.getAllGuests().subscribe({
      next: (data) => {
        this.Guests = data;
        this.isLoading = false;
        this.filteredItems = this.Guests;
      },
      error: (e) => {
        this.isLoading = false;
      },
    });
  }
  //search section
  searchTerm!: string;

  filterItems() {
    this.filteredItems = this.Guests.filter((item) =>
      item.email?.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
}
