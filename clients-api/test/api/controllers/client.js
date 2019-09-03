var should = require('should');
var request = require('supertest');
var server = require('../../../app');

describe('controllers', function() {

  describe('client', function() {

    describe('POST /client', function() {

      it('Deve retornar um erro 403 de validação de CPF', function(done) {

        request(server)
          .post('/client')
          .send({
            "nome": "Gustavo",
            "cpf": "47539897919892",
            "data_nascimento": "2019-09-02",
            "email": "user@example.com",
            "password": "string"
          })
          .set('Accept', 'application/json')
          .expect('Content-Type', /json/)
          .expect(403)
          .end(function(err, res) {

            res.body[0].message.should.eql('CPF inválido');

            done();
          });
      });

    });

  });

});
