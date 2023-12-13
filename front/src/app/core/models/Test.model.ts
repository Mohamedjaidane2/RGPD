import {GuestModel} from "./Guest.model";

export class TestModel {
  id!: string;
  refT!: string;
  guest!: GuestModel;
  score!: number;
  penaltyJail!:  number;
  penaltyAmount!:  number;
  totalCorrectAnswers!:  number;
}
