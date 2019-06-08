-- a

function addSparse (a, b)
    local result = {}
    for k, v in pairs(a) do
        if (b[k] ~= nil) then
            result [k] = v + b[k]
        else
            result [k] = v
        end
    end

    for k, v in pairs(b) do
        if (result[k] == nil) then
            result[k] = v
        end
    end

    return result
end

r = addSparse ({ [1] = 1, [2]= 3, [3]= 4}, { [2]= 2, [4]= 5})
for k, v in pairs (r) do
    print (k, v)
end

-- b
function productSparse (a, b)
    local result = 0
    for k, v in pairs(a) do
        if (b[k] ~= nil) then
            result = result + v * b[k]
        end
    end
    return result
end

print(productSparse ({ [1] = 1, [2]= 3, [3]= 4}, { [2]= 2, [4]= 5}))

