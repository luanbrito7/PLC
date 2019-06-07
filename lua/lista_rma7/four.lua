function num_triang (n)
    local arr = {}
    local last = 0
    for i = 1, n do
        last = last + i
        table.insert(arr, last)
    end
    return arr
end

res = num_triang(6)
for i, v in pairs(res) do
    print (i, v)
end