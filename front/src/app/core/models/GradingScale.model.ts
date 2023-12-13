import { Penalty_status_enum } from "../enum/PenaltyStatus.enum";

export class gradingScaleResponse {
  id!: string;
  penaltyAmount!: number;
  penaltyJail!: number;
  penaltyStatus!: Penalty_status_enum;
  title!: string;
}
export class gradingScaleRequest {
  penaltyAmount!: number;
  penaltyJail!: number;
  penaltyStatus!: string;
}

export class gradingScaleId{
  id!:string
}
export class GetGradingScaleDto{
  id!:string;
  penaltyJail!:number;
  penaltyAmount!:number;
  penaltyStatus!:Penalty_status_enum
}
