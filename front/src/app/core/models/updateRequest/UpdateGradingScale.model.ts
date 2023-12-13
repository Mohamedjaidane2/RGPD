import { Penalty_status_enum } from "../../enum/PenaltyStatus.enum";

export interface UpdateGradingScale {
    penaltyJail: number;
    penaltyAmount:number;
    penaltyStatus: Penalty_status_enum
}