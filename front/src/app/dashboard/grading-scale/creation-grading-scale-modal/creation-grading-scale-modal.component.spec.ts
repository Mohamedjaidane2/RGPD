import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreationGradingScaleModalComponent } from './creation-grading-scale-modal.component';

describe('CreationGradingScaleModalComponent', () => {
  let component: CreationGradingScaleModalComponent;
  let fixture: ComponentFixture<CreationGradingScaleModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreationGradingScaleModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreationGradingScaleModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
