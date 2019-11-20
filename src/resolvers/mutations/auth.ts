import { Customer, Manager } from '../../generated/prisma-client';
import { Context, parseEmail } from '../../utils';
import { hash, compare } from 'bcryptjs';
import { sign } from 'jsonwebtoken';

interface AuthPayLoad {
  token: string;
}

const signUp = async (_parent, args, ctx: Context): Promise<AuthPayLoad> => {
  const email: string = args.email.trim();
  const name: string = args.name.trim();
  const password: string = args.password;
  const cpf: string = args.cpf;
  const birthDate: Date = args.birthDate;

  if (!email || !password || !name || !cpf || !birthDate) throw Error('Fill in all fields!');

  if (!parseEmail.test(email)) throw Error('Bad email format!');

  const emailInUse: boolean = await ctx.prisma.$exists.customer({ email });
  if (emailInUse) throw Error('Email is already in use!');

  const cpfExist: boolean = await ctx.prisma.$exists.customer({ cpf });
  if (cpfExist) throw Error('CPF is already in use!');

  const passwordHash: string = await hash(password, 10);
  const customer: Customer = await ctx.prisma.createCustomer({
    email,
    password: passwordHash,
    name,
    cpf,
    birthDate,
  });

  return {
    token: sign(customer.id, process.env.APP_SECRET),
  };
};

const login = async (_parent, args, ctx: Context): Promise<AuthPayLoad> => {
  const email: string = args.email.trim();
  const password: string = args.password;
  let user = null;

  if (!parseEmail.test(email)) throw Error('Bad email format!');

  user = await ctx.prisma.customer({ email });
  if (!user) user = await ctx.prisma.manager({ email });
  if (!user) throw Error('Invalid Credentials!');

  const valid = await compare(password, user.password);
  if (!valid) throw Error('Invalid Credentials!');

  const token: string = sign(user.id, process.env.APP_SECRET);

  return {
    token,
  };
};

const createManager = async (_parent, args, ctx: Context): Promise<AuthPayLoad> => {
  const email: string = args.email.trim();
  const name: string = args.name.trim();
  const password: string = args.password;

  if (!email || !password || !name) throw Error('Fill in all fields!');

  if (!parseEmail.test(email)) throw Error('Bad email format!');
  const emailInUse: boolean = await ctx.prisma.$exists.manager({ email });
  if (emailInUse) throw Error('Email is already in use!');

  const passwordHash: string = await hash(password, 10);
  const manager: Manager = await ctx.prisma.createManager({
    email,
    password: passwordHash,
    name,
  });

  return {
    token: sign(manager.id, process.env.APP_SECRET),
  };
};

export default {
  signUp,
  login,
  createManager,
};
