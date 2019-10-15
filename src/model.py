#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import sqlite3
import _thread
from socket import socket, AF_INET, SOCK_STREAM
from threading import Thread



# classe de usuários para acesso ao banco
class User:
    def __init__(self):
        self.__nome = None
        self.__senha = None



# classe que acessa o banco de dados
class DAO:
    def __init__(self, usuario):

        try:
            con = sqlite3.connect('usuários.db')
            cur = con.cursor()
            sql = "SELECT nome, senha FROM usuarios"
            data = cur.execute(sql)

            for data in cur.fetchall():
                print(data)

            if data[0] == usuario.__nome and data[1] == usuario.__senha:
                print('login aprovado')
            else:
                print('usuario não encontrado')

        except ConnectionError as msg:
            print('Erro ao conectar-se\n')
            print(msg)
        finally:
            con.close()


# classe que transfere para o banco de dados
class DTO:
    def __init__(self, usuario):

        try:
            con = sqlite3.connect('usuários.db')
            cur = con.cursor()
            sql = "INSERT INTO usuarios (nome, senha) VALUES (?, ?)"
            cur.execute(sql, (usuario.__nome, usuario.__senha))
            con.commit()
            print('transferido')
        except ConnectionError as msg:
            print('Erro ao conectar-se\n')
            print(msg)
        finally:
            con.close()



# classe servidor do chat
class ChatServer:
    def __init__(self):
        self.meuHost = ''  # cria o nome do host
        self.minhaPort = 8080  # define uma porta para usar
        self.sock = socket(AF_INET, SOCK_STREAM)  # cria o socket
        # coloca o servidor no ar
        self.sock.bind((self.meuHost, self.minhaPort))
        self.sock.listen(5)  # escuta na porta 8080 esperando por conexões
        self.despacha()

    def lidaCliente(self):
        while True:
            # recebe os dados do cliente
            self.data = self.conex.recv(1024)

            # se vazio para-se o loop
            if not self.data:
                break

            # escreve e  faz o broadcast da resposta
            self.resposta = self.end, 'disse: %s' % (self.data)
            self.conex.send(self.resposta.encode())

        self.conex.close()

    def despacha(self):
        while True:
            # aceita uma conexão e quando encontra devolve o nome do cliente
            self.conex, self.end = self.sock.accept()
            print(self.end, 'entrou..')

            _thread.start_new_thread(lidaCliente, (self.conex,))


# classe cliente do chat
class ChatClient(Thread):
    def __init__(self, id, server, port, *mensagem):

        self.id = 'localhost'  # id do cliente
        self.server = server  # servidor a ser conectado
        self.port = 8080  # port que será usada
        self.msgs = mensagem  # mensagem que será enviada
        Thread.__init__(self)  # thread q rodará os clientes

    def run(self):
        # criação do socket e conexão no servidor
        self.sock = socket(AF_INET, SOCK_STREAM)
        self.sock.connect(self.server, self.port)

        # manda a mgs linha por linha
        for linha in self.msgs:
            self.sock.send(linha)

            # espera uma resposta do servidor
            self.data = self.sock.recv(1024)
            print('Cliente', self.c, 'recebeu:', self.data)

        self.sock.close()
