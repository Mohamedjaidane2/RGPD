import { Role } from '../../enum/Role.enum';

export interface GuardResponse {
  email: string;
  role: Role;
  isExpired: boolean;
}
