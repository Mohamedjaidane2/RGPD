export class ResponseDto {
  id!: string;
  refT!: string;
  guest!: {
    id: string;
    firstName: string;
    lastName: string;
    function: string;
    telephone: string;
    email: string;
    expertiseField: string;
    profile: string;
    organisation: {
      name: string;
      activityArea: string;
      expertiseField: string;
      workforce: string;
      created: string;
    };
    created: string;
  };
  score!: number;
  created!: string;
}
