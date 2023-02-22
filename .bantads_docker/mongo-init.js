db.createUser({
  user: "bantads",
  pwd: "bantads",
  roles: [
    {
      role: "dbOwner",
      db: "bantads",
    },
  ],
});

db.createCollection('usuarios');

db.usuarios.insertMany([
  {
    nome: 'Admin',
    email: 'admin@email.com',
    senha: 'admin',
    perfil: 'ADMIN',
  },
  {
    nome: 'Gerente Luis',
    email: 'luis@gerente.com',
    senha: 'gerente',
    perfil: 'GERENTE',
  },
])
