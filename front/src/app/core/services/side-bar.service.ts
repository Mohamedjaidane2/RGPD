import { EventEmitter, Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class SideBarService {
  constructor() {}

  public idTest: EventEmitter<string | null> = new EventEmitter<string | null>(true);

  invokeIdTest(idTest: string | null): void {
    this.idTest.emit(idTest)
  }

}
