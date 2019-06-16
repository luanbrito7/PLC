import Control.Concurrent.STM
import Control.Concurrent

main :: IO ()
main = do
    sem <- atomically(newSem)
    forkIO (s sem)
    forkIO (p sem)
    l <- atomically(readTVar sem)
    putStrLn $ show l
    return ()

type Sem = TVar Bool
    
p :: Sem -> IO ()
p sem = atomically(do
            s <- readTVar sem
            if s
                then do
                    writeTVar sem False
                    return ()
                else do
                    retry
        )

s :: Sem -> IO ()
s sem = atomically(do
            s <- readTVar sem
            if s
                then do
                    retry
                else do 
                    writeTVar sem True
                    return ()
        )

newSem :: STM (TVar Bool)
newSem = newTVar False