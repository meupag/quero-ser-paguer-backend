/* eslint-disable no-undef */
const should = require('should');
const request = require('supertest');
const server = require('../../../app');

const CPF = '72849761010';
const username = 'FirstUserTeste';
let id;
describe('Cliente', function () {
  this.timeout(10000);
  it('Deve criar um cliente', (done) => {
    request(server)
      .post('/client')
      .send({
        nome: 'Gustavo Camargo',
        cpf: CPF,
        data_nascimento: '2019-09-02',
        email: 'gutoca77@gmail.com',
        password: 'testePassword123',
        username,
        phoneNumber: '+5511975919064',
      })
      .set('Accept', 'application/json')
      .expect('Content-Type', /json/)
      .expect(200)
      .end((err, res) => {
        console.log(res.body);
        id = res.body.id;
        should.exists(res.body.id);
        done();
      });
  });
});
describe('Cliente', () => {
  describe('PATCH /client', function () {
    this.timeout(10000);
    it('Deve realizar um update de um cliente', (done) => {
      request(server)
        .patch(`/client/${id}`)
        .send({
          nome: 'Gustavo Camargo teste',
          email: 'gutoca77@gmail.com',
        })
        .set('Accept', 'application/json')
        .expect('Content-Type', /json/)
        .expect(200)
        .end((err, res) => {
          res.statusCode.should.eql(200);
          done();
        });
    });
  });
});
describe('Cliente', () => {
  describe('GET /client', function () {
    this.timeout(10000);
    it('Deve retornar um cliente', (done) => {
      request(server)
        .get(`/client/${id}`)
        .set('Accept', 'application/json')
        .expect('Content-Type', /json/)
        .expect(200)
        .end((err, res) => {
          res.statusCode.should.eql(200);
          done();
        });
    });
  });
});
describe('Cliente', function () {
  this.timeout(10000);
  describe('DELETE /client', () => {
    it('Deve excluir um cliente', (done) => {
      request(server)
        .delete(`/client/${id}`)
        .set('Accept', 'application/json')
        .expect('Content-Type', /json/)
        .expect(200)
        .end((err, res) => {
          should.exists(res);
          done();
        });
    });
  });
});
