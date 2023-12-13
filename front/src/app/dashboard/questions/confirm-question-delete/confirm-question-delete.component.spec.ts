import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmQuestionDeleteComponent } from './confirm-question-delete.component';

describe('ConfirmQuestionDeleteComponent', () => {
  let component: ConfirmQuestionDeleteComponent;
  let fixture: ComponentFixture<ConfirmQuestionDeleteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmQuestionDeleteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmQuestionDeleteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
