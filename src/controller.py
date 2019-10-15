#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import model

#classe que controla o login
class LoginController:
    # coloca os dados do login no user e manda para o DAO
    def loga(self, usuario, senha):
        us = model.User()
        us.nome = usuario
        us.senha = senha

        model.DAO(us)

#classe que controla o cadastro
class CadastroController:
    # coloca os dados do cadastro no user e manda para o dao
    def criaCadastro(self, usuario, senha, rpSenha ):
        us = model.User()
        us._nome = usuario

        # testa a igualdade das entradas
        if rpSenha == senha:
            us._senha = senha
            # passa o objeto de usuário para o DAO
            model.DTO(us)
        else:
            print('senhas não conferem')
        



        
