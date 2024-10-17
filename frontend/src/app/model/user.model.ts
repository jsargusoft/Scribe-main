// user.model.ts
export interface User {
    id: number;
    created_at:string;
    firstName: string;
    lastName: string;
    email: string;
    username: string;
    phone: string;
    role : {
      role_id: number;
      name:string;
    };
    password:string;
  }
  