# uberhub-profile-service
Perfis de mentores, especializações e aprovação. Porta 8082

## Firebase

O serviço valida tokens ID do Firebase com o Firebase Admin SDK.

- Configuração padrão: `firebase.enabled=false`
- Para produção: defina `firebase.enabled=true`
- Configure as credenciais padrão do Google no ambiente
- Endpoint de identidade: `GET /api/profile/me`
- Cabeçalho: `Authorization: Bearer <Firebase-ID-Token>`
