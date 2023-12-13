import { Activity_area_enum } from "../enum/ActivityArea.enum";
import { Profile_enum } from "../enum/Profile_enum";
import { Workforce_enum } from "../enum/Workforce.enum";

export class GuestModel{

  created!:Date;
  email!: string;
  expertiseField!: string;
  firstName!: string;
  function!: string;
  id!: string;
  lastName!: string;
  profile!: Profile_enum;
  telephone!: string;
  organisation!: {
    "activityArea": Activity_area_enum,
    "created": string,
    "expertiseField": string,
    "name": string,
    "workforce": Workforce_enum;
  };
}
