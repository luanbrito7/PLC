import Control.Concurrent.STM
import Control.Concurrent

main :: IO ()
main = do
    
    lista <- atomically (newTVar [])
    put lista 10
    a <- get lista
    putStrLn $ show a
    return ()
    -- putStrLn $ (readTVar memoria)

get :: TVar [Integer] -> IO Integer
get mem = do atomically(do (x:xs) <- readTVar mem
                           writeTVar mem xs
                           return x
                        )

put :: TVar [Integer] -> Integer -> IO ()
put mem value = do atomically(do lista <- readTVar mem
                                 writeTVar mem $ lista ++ [value]
                                 return ()
                             )