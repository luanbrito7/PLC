module Main where
import Control.Concurrent
import Control.Concurrent.MVar

main :: IO Integer
main = do
    q <- newEmptyMVar
    putMVar q [2, 1, 3]
    a <- readMVar q
    take' q -- Tiro o 2
    put' q 10 -- Boto 10
    a <- take' q -- Tiro 1
    return a -- E retorno 1

take' :: MVar [Integer] -> IO Integer
take' bq = do
            (x:xs) <- takeMVar bq -- Ler lista separando head e tail
            putMVar bq xs -- Coloca a tail de volta em MVar
            return x -- Retorna head

put' :: MVar [Integer] -> Integer -> IO ()
put' bq num = do
              value <- takeMVar bq 
              putMVar bq $ value ++ [num] -- Insere no final