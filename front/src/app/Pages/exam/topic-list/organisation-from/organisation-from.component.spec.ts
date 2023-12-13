import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganisationFromComponent } from './organisation-from.component';

describe('OrganisationFromComponent', () => {
  let component: OrganisationFromComponent;
  let fixture: ComponentFixture<OrganisationFromComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrganisationFromComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganisationFromComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
