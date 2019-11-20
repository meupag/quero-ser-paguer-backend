import { Context } from '../../utils';
import { Product, Float, Int } from '../../generated/prisma-client';

const createProduct = async (_parent, args, ctx: Context): Promise<Product> => {
  const description: string = args.description.trim();
  const suggestedPrice: Float = args.suggestedPrice;
  const store: Int = args.store;

  if (!suggestedPrice || !description || !store) throw Error('Fill in all fields!');

  const product: Product = await ctx.prisma.createProduct({
    description,
    suggestedPrice,
    store,
  });
  return product;
};

const updateProduct = async (_parent, args, ctx: Context): Promise<Product> => {
  if (!args.id) throw Error('The id field is required!');
  const product: Product = await ctx.prisma.updateProduct({
    data: {
      description: args.description,
      suggestedPrice: args.suggestedPrice,
      store: args.store,
    },
    where: {
      id: args.id,
    },
  });

  return product;
};

const deleteProduct = async (_parent, args, ctx: Context): Promise<Product> => {
  if (!args.id) throw Error('The id field is required!');
  const product: Product = await ctx.prisma.updateProduct({
    data: {
      deleted: true,
    },
    where: {
      id: args.id,
    },
  });
  return product;
};

export default {
  createProduct,
  updateProduct,
  deleteProduct,
};
