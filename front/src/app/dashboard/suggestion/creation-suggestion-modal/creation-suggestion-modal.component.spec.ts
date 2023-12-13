import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreationSuggestionModalComponent } from './creation-suggestion-modal.component';

describe('CreationSuggestionModalComponent', () => {
  let component: CreationSuggestionModalComponent;
  let fixture: ComponentFixture<CreationSuggestionModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreationSuggestionModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreationSuggestionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
