import { Prisma } from './generated/prisma-client';
import { verify } from 'jsonwebtoken';

export const parseEmail = /^[a-z0-9.]+@[a-z0-9]+\.[a-z]+(\.[a-z]+)?$/i;

export interface Context {
  prisma: Prisma;
  request: any;
}

export const getUserId = (ctx: Context): string => {
  const authorization = ctx.request.get('authorization');

  if (authorization) {
    const token = authorization.replace('Bearer ', '');
    const userId = verify(token, process.env.APP_SECRET);

    return userId.toString();
  }
};
