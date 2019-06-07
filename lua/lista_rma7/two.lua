local dados = { clattes = {nome = "Cesar",
sobrenome = "Lattes",
ano_nascimento = 1924,
ano_falecimento = 2005,
contribuicao = "meson pi"},
jpalis = {nome = "Jacob",
sobrenome = "Palis",
ano_nascimento = 1940,
ano_falecimento = 0000,
contribuicao = "sistemas dinamicos"}}

function tabConsistencia (data)
    for ki, vi in pairs (data) do
        for kj, vj in pairs (data) do
            for kk, v in pairs (vj) do
                if (vi[kk] == nil) then
                    return false
                end
            end
        end
    end
    return true
end

print(tabConsistencia(dados))