import { Context } from '../../utils';
import { Order, OrderItemCreateWithoutOrderInput, Int, Float, UUID, OrderItem } from '../../generated/prisma-client';
import { getUserId } from '../../utils';

interface CreateOrUpdadeOrderItem {
  id?: UUID;
  quantity?: Int;
  price?: Float;
  productId?: UUID;
  discount?: Float;
}

const createOrder = async (_parent, args, ctx: Context): Promise<Order> => {
  const userId = getUserId(ctx);
  const listOrderItens: CreateOrUpdadeOrderItem[] = args.orderItens;
  let total = 0;
  let subTotal = 0;
  const listOrderItensToCreate: OrderItemCreateWithoutOrderInput[] = [];

  listOrderItens.forEach(async orderItem => {
    subTotal += orderItem.price * orderItem.quantity;
    total += orderItem.price * orderItem.quantity - (orderItem.discount ? orderItem.discount : 0);
    listOrderItensToCreate.push({
      discount: orderItem.discount,
      price: orderItem.price,
      quantity: orderItem.quantity,
      product: {
        connect: {
          id: orderItem.productId,
        },
      },
    });
  });

  const order: Order = await ctx.prisma.createOrder({
    total,
    subTotal,
    orderItens: {
      create: listOrderItensToCreate,
    },
    customer: {
      connect: { id: userId },
    },
  });

  return order;
};

const cancelOrder = async (_parent, args, ctx: Context): Promise<Order> => {
  if (!args.id) throw Error('The id field is required!');

  const order = await ctx.prisma.order({ id: args.id });
  if (order.statusOrder == 'APPROVED') throw Error('This order has already been approved, impossible to cancel');

  const listOrderItens: OrderItem[] = await ctx.prisma.orderItems({
    where: {
      order: {
        id: args.id,
      },
    },
  });

  const listToUpdateOrdemItens = [];
  listOrderItens.forEach(orderItem => {
    listToUpdateOrdemItens.push({
      data: {
        status: 'CANCELED',
      },
      where: {
        id: orderItem.id,
      },
    });
  });

  const Order: Order = await ctx.prisma.updateOrder({
    data: {
      statusOrder: 'CANCELED',
      orderItens: {
        updateMany: listToUpdateOrdemItens,
      },
    },
    where: {
      id: args.id,
    },
  });

  return Order;
};

export default {
  createOrder,
  cancelOrder,
};
