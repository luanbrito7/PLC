local dados = { clattes = {nome = "Cesar",
sobrenome = "Lattes",
ano_nascimento = 1924,
ano_falecimento = 2005,
area = "fisica",
contribuicao = "meson pi"},
jpalis = {nome = "Jacob",
sobrenome = "Palis",
ano_nascimento = 1940,
ano_falecimento = 0000,
contribuicao = "sistemas dinamicos"}}

function getKeys(data)
    set = {}
    for k, v in pairs(data) do
        for k1, v1 in pairs (v) do
           set [k1] = true 
        end
    end

    arr = {}
    for k,_ in pairs(set) do
        table.insert(arr, k)
    end

    return arr
end

result = getKeys(dados)
for i, k in pairs(result) do
    print (i, k)
end